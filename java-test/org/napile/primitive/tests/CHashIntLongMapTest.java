/*
 * Primitive Collection Framework for Java
 * Copyright (C) 2011 napile.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.napile.primitive.tests;

import org.napile.primitive.maps.IntLongMap;
import org.napile.primitive.maps.impl.CHashIntLongMap;
import org.testng.annotations.Test;

/**
 * @author VISTALL
 * @date 23:17/13.10.2011
 */
public class CHashIntLongMapTest
{
	@Test(threadPoolSize = 50, invocationCount = 1)
	public void test() throws Exception
	{
		IntLongMap map = new CHashIntLongMap();
		map.put(268480666, Long.MAX_VALUE);


		System.out.println("get(268480666): " + map.get(268480666));
		System.out.println("get(-1): " + map.get(-1));
		System.out.println("get(Integer.MAX_VALUE): " + map.get(Integer.MAX_VALUE));
		System.out.println("containsKey(-1): " + map.containsKey(-1));
		System.out.println("containsKey(268480666): " + map.containsKey(268480666));
		System.out.println("containsValue(Long.MAX_VALUE: " + map.containsValue(Long.MAX_VALUE));
	}
}
