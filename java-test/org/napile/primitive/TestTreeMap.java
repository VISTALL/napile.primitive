/**
 * Primitive Collection Framework for Java
 * Copyright (C) 2010 Napile.org
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
package org.napile.primitive;

import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

/**
 * @author: VISTALL
 * @date:  13:14/25.12.2010
 */
public class TestTreeMap
{
	public static void main(String... arg)
	{
		IntObjectMap<String> map = new TreeIntObjectMap<String>();
		map.put(-1, "String");
		map.put(0, "Test");
		map.put(100, "aa");
		map.put(Integer.MIN_VALUE, "44324324");

		for(IntObjectMap.Entry<String> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
}
