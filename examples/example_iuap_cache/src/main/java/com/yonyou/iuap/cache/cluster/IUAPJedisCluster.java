package com.yonyou.iuap.cache.cluster;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterCommand;
import redis.clients.jedis.exceptions.JedisConnectionException;


/** 为模拟异常，继承  JedisCluster并重写里面的 set方法 */
public class IUAPJedisCluster extends JedisCluster {
	

	public IUAPJedisCluster(HostAndPort node) {
		super(node);
	}

	public IUAPJedisCluster(HostAndPort node, GenericObjectPoolConfig poolConfig) {
		super(node, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int timeout, GenericObjectPoolConfig poolConfig) {
		super(node, timeout, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int timeout, int maxAttempts, GenericObjectPoolConfig poolConfig) {
		super(node, timeout, maxAttempts, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int connectionTimeout, int soTimeout, int maxAttempts, GenericObjectPoolConfig poolConfig) {
		super(node, connectionTimeout, soTimeout, maxAttempts, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig poolConfig) {
		super(node, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int timeout, int maxAttempts) {
		super(node, timeout, maxAttempts);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(HostAndPort node, int timeout) {
		super(node, timeout);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> nodes, GenericObjectPoolConfig poolConfig) {
		super(nodes, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> nodes, int timeout, GenericObjectPoolConfig poolConfig) {
		super(nodes, timeout, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> jedisClusterNode, int timeout, int maxAttempts, GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, timeout, maxAttempts, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxAttempts, GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> nodes, int timeout, int maxAttempts) {
		super(nodes, timeout, maxAttempts);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> nodes, int timeout) {
		super(nodes, timeout);
		// TODO Auto-generated constructor stub
	}

	public IUAPJedisCluster(Set<HostAndPort> nodes) {
		super(nodes);
		// TODO Auto-generated constructor stub
	}
	
	
	/**模拟连接失败  */
	 @Override
	  public String set(final String key, final String value) {
	    return new JedisClusterCommand<String>(connectionHandler, maxAttempts) {
	      @Override
	      public String execute(Jedis connection) {
	    	
	    	int k = Integer.parseInt(key) ;
	    	if( k %100 !=0 ){
	    		throw new JedisConnectionException("模拟异常。") ;
	    	}
	    	  
	        return connection.set(key, value);
	      }
	    }.run(key);
	  }
	 
	 

}
