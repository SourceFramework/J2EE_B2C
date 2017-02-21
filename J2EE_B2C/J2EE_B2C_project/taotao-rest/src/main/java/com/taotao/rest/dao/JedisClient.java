package com.taotao.rest.dao;

public interface JedisClient {
	/**
	 * Get the value of the specified key. If the key does not exist null is
	 * returned. If the value stored at key is not a string an error is returned
	 * because GET can only handle string values.
	 */
	public String get(String key);

	/**
	 * Set the string value as value of the key. The string can't be longer than
	 * 1073741824 bytes (1 GB)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value);

	/**
	 * If key holds a hash, retrieve the value associated to the specified
	 * field. If the field is not found or the key does not exist, a special
	 * 'nil' value is returned.
	 * 
	 * @param hkey
	 * @param key
	 * @return
	 */
	public String hget(String key, String field);

	/**
	 * Set the specified hash field to the specified value. If key does not
	 * exist, a new key holding a hash is created.
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return If the field already exists, and the HSET just produced an update
	 *         of the value, 0 is returned, otherwise if a new field is created
	 *         1 is returned.
	 */
	public long hset(String key, String field, String value);

	/**
	 * Increment the number stored at key by one. If the key does not exist or
	 * contains a value of a wrong type, set the key to the value of "0" before
	 * to perform the increment operation.
	 * 
	 * @param key
	 * @return Integer reply, this commands will reply with the new value of key
	 *         after the increment.
	 */
	public long incr(String key);

	/**
	 * Set a timeout on the specified key. After the timeout the key will be
	 * automatically deleted by the server. A key with an associated timeout is
	 * said to be volatile in Redis terminology.
	 * 
	 * @param key
	 * @param seconds
	 * @return Integer reply, specifically: 1: the timeout was set. 0: the
	 *         timeout was not set since the key already has an associated
	 *         timeout (this may happen only in Redis versions < 2.1.3, Redis >=
	 *         2.1.3 will happily update the timeout), or the key does not
	 *         exist.
	 */
	public long expire(String key, int seconds);

	/**
	 * The TTL command returns the remaining time to live in seconds of a key
	 * that has an EXPIRE set. This introspection capability allows a Redis
	 * client to check how many seconds a given key will continue to be part of
	 * the dataset.
	 * 
	 * @param key
	 * @return Integer reply, returns the remaining time to live in seconds of a
	 *         key that has an EXPIRE. In Redis 2.6 or older, if the Key does
	 *         not exists or does not have an associated expire, -1 is returned.
	 *         In Redis 2.8 or newer, if the Key does not have an associated
	 *         expire, -1 is returned or if the Key does not exists, -2 is
	 *         returned.
	 */
	public long ttl(String key);

	public long del(String key);

	/**
	 * Remove the specified field from an hash stored at key.
	 * 
	 * @param key
	 * @param field
	 * @return If the field was present in the hash it is deleted and 1 is
	 *         returned, otherwise 0 is returned and no operation is performed.
	 */
	public long hdel(String key, String field);
}
