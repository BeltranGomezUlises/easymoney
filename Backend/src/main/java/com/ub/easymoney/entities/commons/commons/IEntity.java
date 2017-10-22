/*
 * Copyright (C) 2017 Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ub.easymoney.entities.commons.commons;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * clase padre de cualquier entidad de la configuracion general
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class IEntity {
     
    public abstract Object getId();
                
}
