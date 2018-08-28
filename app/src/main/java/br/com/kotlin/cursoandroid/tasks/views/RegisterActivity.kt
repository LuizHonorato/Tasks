package br.com.kotlin.cursoandroid.tasks.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.kotlin.cursoandroid.tasks.R
import br.com.kotlin.cursoandroid.tasks.business.UserBusiness
import br.com.kotlin.cursoandroid.tasks.repository.UserRepository
import br.com.kotlin.cursoandroid.tasks.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness : UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
         //Eventos
        setListeners()

        //Instanciar variáveis da classe
        mUserBusiness = UserBusiness(this)
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun handleSave() {

        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            //Inserção do usuário
            mUserBusiness.insert(name, email, password)

            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.erro_inesperado, Toast.LENGTH_LONG).show()
        }
    }
}
