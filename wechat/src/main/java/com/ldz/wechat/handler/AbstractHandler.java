package com.ldz.wechat.handler;

import me.chanjar.weixin.cp.message.WxCpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
public abstract class AbstractHandler implements WxCpMessageHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());
}
