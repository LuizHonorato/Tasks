package br.com.kotlin.cursoandroid.tasks.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.com.kotlin.cursoandroid.tasks.R
import br.com.kotlin.cursoandroid.tasks.adapter.TaskListAdapter
import br.com.kotlin.cursoandroid.tasks.business.TaskBusiness
import br.com.kotlin.cursoandroid.tasks.constants.TaskConstants
import br.com.kotlin.cursoandroid.tasks.entities.OnTaskListFragmentInteractionListener
import br.com.kotlin.cursoandroid.tasks.util.SecurityPreferences
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener : OnTaskListFragmentInteractionListener
    private var mTaskFilter : Int = 0

    companion object {
        fun newInstance(taskFilter : Int): TaskListFragment {
            val args : Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTaskFilter = arguments!!.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context

        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        mListener = object : OnTaskListFragmentInteractionListener {

            override fun onListClick(taskId: Int) {

                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(mContext, TaskFormActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                loadTasks()
                Toast.makeText(mContext, getString(R.string.task_deleted), Toast.LENGTH_LONG).show()
            }

            override fun onUncompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, false)
                loadTasks()
            }

            override fun onCompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, true)
                loadTasks()
            }
        }

        //Obtendo o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        //Definindo o adapter com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListener)

        //Definir o layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }



}
