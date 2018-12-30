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
package io.github.joealisson.primitive;

import io.github.joealisson.primitive.maps.IntObjectMap;
import io.github.joealisson.primitive.maps.impl.CHashIntObjectMap;

/**
 * @author VISTALL
 *
 */
public class TestMap
{
    public static void main(String... ar)
    {
        //IntObjectMap<String> map = new HashIntObjectMap <String>();
        IntObjectMap<Long> map = new CHashIntObjectMap<Long>();
        for(int i = 0; i < (Integer.MAX_VALUE & 0xFFFF); i++)
            map.put(i, (long)i);

        System.out.println(map.size());

        //	for(IntObjectMap.Entry<Long> entry : map.entrySet())
        //		System.out.println(entry.getKey() + " " + entry.getValue());
    }
}
