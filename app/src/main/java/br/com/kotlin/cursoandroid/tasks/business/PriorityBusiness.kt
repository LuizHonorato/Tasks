package br.com.kotlin.cursoandroid.tasks.business

import android.content.Context
import br.com.kotlin.cursoandroid.tasks.entities.PriorityEntity
import br.com.kotlin.cursoandroid.tasks.repository.PriorityRepository

class PriorityBusiness (context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}