package com.yonyou.iuap.security.esapi;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yonyou.iuap.security.utils.TokenGenerator;

public class TokenGeneratorTest {
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGeneratorSeed() throws EncryptException{
		String seed = TokenGenerator.genSeed();
		System.out.println(seed);
	}
	
	@Test
	public void testGeneratorToken() throws EncryptException{
		String seed = TokenGenerator.genSeed();
		String token1 = TokenGenerator.genToken("user01", System.currentTimeMillis(), seed);
		String token2 = TokenGenerator.genToken("user02", System.currentTimeMillis(), seed);
		System.out.println(token1);
		System.out.println(token2);
	}
	
	@Test
	public void testWithThreads() throws InterruptedException {
		
		IUAPESAPI.encryptor();
		
		final int hashIterations = 1024;
		
		Thread.sleep(3000);
		final int threadNum = 100;
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; ++i) {
			Callable<Void> task = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					try {
						int size = 1000;
						String[] salts = new String[size];
						for(int k = 0;k<size;k++){
							String salt = new Random().nextInt(10000)+"";
							salts[k] = salt;
						}
						String text = "asfasdfasdfasdfastrhdfgafwr2fsfsdtw42fdsfaadafdsaya";
						
						long t1 = System.currentTimeMillis();
						for(int k = 0;k<size;k++){
							String salt =salts[k];
							IUAPESAPI.encryptor().hash(text + k, salt, hashIterations);
						}
						long t2 = System.currentTimeMillis();
						
						long time = t2 - t1;
						System.err.println(time);
						
					} catch (Throwable e) {
						e.printStackTrace();
					} 
					
					return null;
				}
			};
			service.submit(task);
			
		}
		service.shutdown();
		service.awaitTermination(10, TimeUnit.MINUTES);
		System.out.println("----end----");
		
	}
}
