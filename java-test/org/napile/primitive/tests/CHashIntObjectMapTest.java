package org.napile.primitive.tests;

import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;

/**
 * @author VISTALL
 * @date 20:19/12.10.2011
 */
public class CHashIntObjectMapTest
{
	private IntObjectMap<Long> _map = new CHashIntObjectMap<Long>();

	//@Test(threadPoolSize = 50, invocationCount = 1000)
	public void test() throws Exception
	{
		_map.put(268480666, Long.MAX_VALUE);

		Long val = _map.get(-1);
		for(IntObjectMap.Entry a : _map.entrySet())
			System.out.println(a.toString());

		_map.clear();
	}
	/*
	@Test(singleThreaded = true, invocationCount = 1000, invocationTimeOut = 1)
	public void testSafe() throws Exception
	{
		_map.put(268480666, Long.MAX_VALUE);

		Long v = _map.get(268480666);
		Long v2 = _map.get(268480661);

		_map.clear();

		Assert.assertNull(v2);
		Assert.assertNotNull(v);
	} */
}
