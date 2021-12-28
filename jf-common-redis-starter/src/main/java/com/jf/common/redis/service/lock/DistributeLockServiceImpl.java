package com.jf.common.redis.service.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: redisson实现分布式锁接口实现类
 *
 * @author: 江峰
 * @create: 2021-03-23 18:48
 * @since: 2.22.1
 */
@Service
public class DistributeLockServiceImpl implements DistributeLockService {

	@Autowired
	private RedissonClient redissonClient;

	@Override
	public void lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}

	@Override
	public void lock(String lockKey, int leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(leaseTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public void lock(String lockKey, int leaseTime, TimeUnit unit) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(leaseTime, unit);
	}

	@Override
	public boolean tryLock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.tryLock();
	}

	@Override
	public boolean tryLock(String lockKey, long waitTime, long leaseTime,
			TimeUnit unit) throws InterruptedException {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.tryLock(waitTime, leaseTime, unit);
	}

	@Override
	public boolean tryLock(String lockKey, long leaseTime, TimeUnit unit)
			throws InterruptedException {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.tryLock(0L, leaseTime, unit);
	}

	@Override
	public boolean isLocked(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.isLocked();
	}

	@Override
	public boolean isHeldByCurrentThread(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		return lock.isHeldByCurrentThread();
	}

}