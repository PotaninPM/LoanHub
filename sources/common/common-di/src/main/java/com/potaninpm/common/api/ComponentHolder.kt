package com.potaninpm.common.api

abstract class ComponentHolder<C : DIComponent> {

    @Volatile
    private var component: C? = null

    protected abstract fun build(): C

    // юзается проверка double check locking
    fun get(): C {
        return component ?: synchronized(this) {
            component ?: build().also { component = it }
        }
    }

    fun set(instance: C) {
        synchronized(this) {
            component = instance
        }
    }

    fun clear() {
        synchronized(this) {
            component = null
        }
    }
}
