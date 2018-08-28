package br.com.kotlin.cursoandroid.tasks.business

import android.content.Context
import br.com.kotlin.cursoandroid.tasks.R
import br.com.kotlin.cursoandroid.tasks.constants.TaskConstants
import br.com.kotlin.cursoandroid.tasks.entities.UserEntity
import br.com.kotlin.cursoandroid.tasks.repository.UserRepository
import br.com.kotlin.cursoandroid.tasks.util.SecurityPreferences
import br.com.kotlin.cursoandroid.tasks.util.ValidationException

class UserBusiness (val context: Context) {

    private val mUserRepository : UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login (email: String, password: String) : Boolean {
        val user : UserEntity? = mUserRepository.get(email, password)
        return if (user != null) {
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            true
        } else {
            false
        }
    }

    fun insert (name: String, email: String, password: String) {
        try {

            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_campos))
            }

            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_em_uso))
            }

            val userId = mUserRepository.insert(name, email, password)

            //Salvar no SharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)

        } catch (e: Exception) {
            throw e
        }
    }

}