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
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 * @param <K> tipo de dato de llave primaria
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IEntity<K> {

    /**
     * todas las entidades necesitan tener una forma de identificarse, en este metodo se retorna el atributo que indentifica a una entidad como unica, puede ser un atributo simple o un modelo que encapsule atributos y convierta el id en una llave compuesta
     *
     * @return objeto que representa la llave unica de la entidad
     */
    public K obtenerIdentificador();

}
