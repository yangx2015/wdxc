package com.ldz.biz.module.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.mapper.ClZdglMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClCssd;
import com.ldz.biz.module.model.ClZdgl;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.CssdService;
import com.ldz.biz.module.service.ZdglService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZdglServiceImpl extends BaseServiceImpl<ClZdgl,String> implements ZdglService{
    @Autowired
    private ClZdglMapper entityMapper;
    @Autowired
    private ClService clService;
    @Autowired
    private CssdService cssdService;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Override
    protected Mapper<ClZdgl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZdgl.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZdgl entity) {
    	ClZdgl findById = findById(entity.getZdbh());
    	if (findById!=null) {
			return ApiResponse.fail("终端编号已存在");
		}
    	//默认设置终端在线状态为离线
    	entity.setZxzt("20");
    	
    	//默认设备碰撞灵敏度
    	entity.setPzlmd("10");
    	//默认设备急加速灵敏度
    	entity.setJslmd("2");
    
    	//默认设备视屏上传模式
    	entity.setSpscms("20");
    	//默认设备的心跳
    	entity.setGpsxt("10");
    	
        entity.setCjr(getOperateUser());
        entity.setCjsj(new Date());
        save(entity);
        return ApiResponse.saveSuccess();
    }

	@Override
	public void insetAndUpdate(ClZdgl entity) {
		
	 boolean flag= ifExists("zdbh",entity.getZdbh());
		if (flag==true) {
			update(entity);
		}else {
			save(entity);
		}
	}

    @Override
    public ApiResponse<String> updateEntity(ClZdgl entity) {
//    	ClZdgl findById = findById(entity.getZdbh());
//    	if (findById!=null) {
//			return ApiResponse.fail("终端编号已存在");
//		}
        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }

    public ApiResponse<List<ClZdgl>> unboundList(){
//        1、定义初始变量
        ApiResponse<List<ClZdgl>> result = new ApiResponse<List<ClZdgl>>();
        List<ClZdgl> list=entityMapper.getUnboundList();
        result.setResult(list);
        return result;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        String cphLike = getRequestParamterAsString("cphLike");
        if (StringUtils.isNotEmpty(cphLike)){
            List<ClCl> carList = clService.findLike(ClCl.InnerColumn.cph,cphLike);
            if (CollectionUtils.isEmpty(carList)){
                return false;
            }
            List<String> zdbhs = carList.stream().map(ClCl::getZdbh).collect(Collectors.toList());
            condition.in(ClZdgl.InnerColumn.zdbh,zdbhs);
        }
        return true;
    }
    /**
     * 自定义分页的对象
     * @param resultPage
     */
    protected void afterPager(PageInfo<ClZdgl> resultPage){
        if(resultPage!=null){
            List<ClZdgl> list=resultPage.getList();
            if(list!=null&&list.size()>0){
                List<String> listIds = list.stream().map(ClZdgl::getZdbh).collect(Collectors.toList());
                List<ClCl> clList=clService.findIn(ClCl.InnerColumn.zdbh,listIds);
                Map<String,ClZdgl> zdbhMap = list.stream().collect(Collectors.toMap(ClZdgl::getZdbh, p->p));

                if(clList!=null&&clList.size()>0){
                    for(ClCl cl:clList){
                        ClZdgl zdbh=zdbhMap.get(cl.getZdbh());
                        if (zdbh == null){
                            continue;
                        }

                        zdbh.setCl(cl);
                        zdbh.setCph(cl.getCph());
                        // 获取速度上限
                        List<ClCssd> eq = cssdService.findEq(ClCssd.InnerColumn.cph.name(), cl.getCph());
                        if(!CollectionUtils.isEmpty(eq)){
                            zdbh.setCssd(eq.get(0).getSdsx());
                        }

                    }
                }
            }
        }
    }

	@Override
	public ApiResponse<Map<String, Integer>> getzdxc() {
		int dianhuo =0;
		int xihuo =0;
		int lixian=0;
		List<ClZdgl> selectAll = entityMapper.selectAll();
	       RuntimeCheck.ifEmpty(selectAll, "暂无设备");
		for (ClZdgl clZdgl : selectAll) {
			//点火
			if (StringUtils.equals(clZdgl.getZxzt(), "00")) {
				dianhuo++;
			}
			//熄火
			if (StringUtils.equals(clZdgl.getZxzt(), "10")) {
				xihuo++;
			}
			//离线
			if (StringUtils.equals(clZdgl.getZxzt(), "20")) {
				lixian++;
			}
			
		}
		Map<String,Integer> map = new HashMap<>();
		map.put("设备总数", selectAll.size());
		map.put("设备在线数量", dianhuo);
		map.put("设备熄火数量", xihuo);
		map.put("设备离线数量", lixian);
		ApiResponse<Map<String, Integer>>  apiResponse = new ApiResponse<>();
		apiResponse.setResult(map);
		
		return apiResponse;
	}

    @Override
    public ApiResponse<String> getVersionInfo(String deviceId) {
        String val = (String) redisTemplateUtil.boundValueOps("versionInfo-"+deviceId).get();
        if (StringUtils.isEmpty(val)){
            return ApiResponse.fail("暂无版本信息");
        }
        return ApiResponse.success(val);
    }

    /**
     * 解析excel 批量导入终端
     * @param
     * @return
     */
    @Override
    public  ApiResponse<String> saveBatch(String filePath) throws IOException {

       /* Workbook workbook;
        try {
            if(filePath.indexOf(".xlsx")>-1){
                workbook = new XSSFWorkbook(new FileInputStream(filePath));
            } else {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
            }
            //HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead)); //2003 创建对Excel工作簿文件的引用
            //XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead)); //2007,2010 创建对Excel工作簿文件的引用
            Sheet sheet = workbook.getSheetAt(0); // 创建对工作表的引用
            int rows = sheet.getPhysicalNumberOfRows();// 获取表格的
            int columns = 0;
            for (int r = 0; r < rows; r++) { // 循环遍历表格的行
                if(r==0){
                    //在第一行标题行计算出列宽度,因为数据行中可能会有空值
                    columns = sheet.getRow(r).getLastCellNum();
                    continue;
                }
                String value = "";
                Row row = sheet.getRow(r); // 获取单元格中指定的行对象
                if (row != null) {
                    //int cells = row.getPhysicalNumberOfCells();// 获取一行中的单元格数
                    //int cells = row.getLastCellNum();// 获取一行中最后单元格的编号（从1开始）
                    for (short c = 0; c < columns; c++) { // 循环遍历行中的单元格
                        Cell cell = row.getCell((short) c);
                        if (cell != null) {

                            ClZdgl clZdgl = new ClZdgl();
                            if(cell.getCellType() != Cell.CELL_TYPE_STRING){

                                return ApiResponse.fail("第" + r + "行 , " + "第" + c + "列的数据不是文本类型,请改成文本类型后上传" );
                            }
                            String v = cell.getStringCellValue();
                            switch(c){
                                case 0: // 终端号
                                    if(StringUtils.isEmpty(v)){
                                        return ApiResponse.fail("第" + r + "行 , " + "第" + c + "列的设备终端号不能为空" );
                                    }
                                    ClZdgl zdgl = findById(cell.getStringCellValue());
                                    if(zdgl == null){
                                        return ApiResponse.fail("第" + r + "行 , " + "第" + c + "列的设备终端号已经存在" );
                                    }
                                    clZdgl.setZdbh(cell.getStringCellValue());
                                    break;
                                case 1: // 名称
                                    if(StringUtils.isEmpty(v)){
                                        return ApiResponse.fail("第" + r + "行 , " + "第" + c + "列的设备名称不能为空" );
                                    }
                                    clZdgl.setMc(v);
                                    break;
                                case 2: // 型号
                                    if(StringUtils.isEmpty(v)){
                                        return ApiResponse.fail("第" + r + "行 , " + "第" + c + "列的设备型号不能为空" );
                                    }
                                    clZdgl.setXh(v);
                                    break;
                                case 3: // 碰撞灵敏度
                                    if(StringUtils.isEmpty(v)){
                                        clZdgl.setPzlmd("10");
                                    }else{
                                        clZdgl.setPzlmd(v);
                                    }
                                    break;
                                case 4: //上传视频模式




                            }






                        }
                    }
                }
                String[] str = value.split(",");
                System.out.println(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/





        return ApiResponse.success();
    }


    public static void main(String[] args) throws IOException {

        ZdglServiceImpl a = new ZdglServiceImpl();
        a.saveBatch(null);

    }



}
