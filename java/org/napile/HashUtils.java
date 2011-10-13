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

package org.napile;

/**
 * @author VISTALL
 * @date 22:45/13.10.2011
 */
public class HashUtils
{
	public static int hashCode(int val)
	{
		return val;
	}

	public static int hashCode(long val)
	{
		return (int)(val ^ (val >>> 32));
	}

	public static int hashCode(Object val)
	{
		return val == null ? 0 : val.hashCode();
	}
}
