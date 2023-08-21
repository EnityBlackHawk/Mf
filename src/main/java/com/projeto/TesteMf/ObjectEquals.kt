package com.projeto.TesteMf

import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible



fun <T : Any> hasSameValues(objA : T, objB : T) : Boolean
{
    val aProps = objA::class.declaredMemberProperties
    val bProps = objB::class.declaredMemberProperties

    for(aP in aProps)
    {
        val bP = bProps.filter { it.name == aP.name }
        if(bP.isEmpty())
            return false

        aP.isAccessible = true
        bP.first().isAccessible = true

        val aValue = aP.getter.call(objA)
        val bValue = bP.first().getter.call(objB)

        if((aValue == null) xor (bValue == null))
            return false

        if(aValue != null)
        {
            if(aValue::class.allSuperclasses.any {
                it == Comparable::class
                    })
            {
                if(aValue != bValue)
                    return false
            }

            else
            {
                val rec = hasSameValues(aValue, bValue!!)
                if(!rec)
                    return false
                else
                    continue
            }
        }


    }

    return true
}