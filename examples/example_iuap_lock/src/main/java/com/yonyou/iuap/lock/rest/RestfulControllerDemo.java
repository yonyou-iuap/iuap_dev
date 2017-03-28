package com.yonyou.iuap.lock.rest;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.lock.DistributedLock;
import com.yonyou.iuap.lock.LockTemplate;
import com.yonyou.iuap.lock.exception.LockException;
import com.yonyou.iuap.lock.zklock.AbstractLockAction;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/lock/demo")
public class RestfulControllerDemo {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "locktemplate", method = RequestMethod.GET)
	public @ResponseBody Object lockTemplateTest(HttpServletRequest request, HttpServletResponse response) {
		final JSONObject json = new JSONObject();
		logger.info("lock test start ... ");
		final String id = "test_" + System.currentTimeMillis();
		try {
			LockTemplate.execute(new AbstractLockAction(id) {
				@Override
				public void doInLock() throws LockException {
					json.put("result", "lock busi success! " + id);
				}
			});
		} catch (LockException e) {
			logger.info("", e);
			json.put("result", "lock busi fail!");
		}

		return json.toString();
	}

	@RequestMapping(value = "simplerest", method = RequestMethod.GET)
	public @ResponseBody String simplerest(HttpServletRequest request, HttpServletResponse response) {
		return "simplerest success!";
	}

	@RequestMapping(value = "disatributelock", method = RequestMethod.GET)
	public @ResponseBody String disLockTest(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		final String lockpath = "iuapdislockpath_test_" + new Random().nextInt(10000);
		boolean islocked = false;
		try {
			long time1 = System.currentTimeMillis();
			islocked = DistributedLock.lock(lockpath);
			if (islocked) {
				long time2 = System.currentTimeMillis() - time1;
				result += "加锁成功，耗用时间为:" + time2 + " 毫秒!";
			}
		} catch (Exception e) {
			logger.info("lock error for " + lockpath);
		} finally {
			if (islocked) {
				long t7 = System.currentTimeMillis();
				DistributedLock.unlock(lockpath);
				long time6 = System.currentTimeMillis() - t7;
				String msg = "解锁时间:" + time6 + " 毫秒!";
				result += msg;
			} else {
				result = "加锁失败!";
			}
		}

		return result;
	}

	@RequestMapping(value = "lockwithoutrelease", method = RequestMethod.GET)
	public @ResponseBody String disLockWithoutReleaseTest(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		final String lockpath = "iuapdislockpath_test_" + new Random().nextInt(10000);
		boolean islocked = false;
		try {
			long time1 = System.currentTimeMillis();
			islocked = DistributedLock.lock(lockpath);
			if (islocked) {
				long time2 = System.currentTimeMillis() - time1;
				result += "加锁成功，耗用时间为:" + time2 + " 毫秒!";
			}
		} catch (Exception e) {
			logger.info("lock error for " + lockpath);
		} finally {
			if (islocked) {
				long t7 = System.currentTimeMillis();
				// 测试如果不解锁，过滤器会最终清除锁，特殊场景中使用，不建议
				DistributedLock.unlock(lockpath);
				long time6 = System.currentTimeMillis() - t7;
				String msg = "解锁时间:" + time6 + " 毫秒!";
				result += msg;
			} else {
				result = "加锁失败!";
			}
		}

		return result;
	}

	@RequestMapping(value = "disatributelockdebug", method = RequestMethod.GET)
	public @ResponseBody Object disLockTestDebug(HttpServletRequest request, HttpServletResponse response) {
		final JSONObject json = new JSONObject();
		logger.info("lock test start ... ");
		final String lockpath = "iuapdislockpath_test_" + new Random().nextInt(10000);
		long time3 = 0;
		long time1 = 0;
		long time2 = 0;
		long time4 = 0;
		long time6 = 0;
		boolean islocked = false;

		try {
			time1 = System.currentTimeMillis();
			islocked = DistributedLock.lock(lockpath);
			if (islocked) {
				time2 = System.currentTimeMillis();
				time3 = time2 - time1;
				logger.info("加锁成功，耗用时间为:" + time3 + " 毫秒!");
				json.put("result", "加锁成功，耗用时间为:" + time3 + " 毫秒!");
			}
		} catch (Exception e) {
			time4 = System.currentTimeMillis();
			time3 = time4 - time1;
			logger.info("锁服务调用失败，耗用时间为:" + time3 + " 毫秒!");
		} finally {
			if (islocked) {
				long t7 = System.currentTimeMillis();
				DistributedLock.unlock(lockpath);
				time6 = System.currentTimeMillis() - t7;
				logger.info("解锁需要的时间:" + time6 + " 毫秒!");
			} else {
				logger.error("加锁失败!");
				json.put("result", "加锁失败!");
			}
		}

		return json.toString();
	}

}
