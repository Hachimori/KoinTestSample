package com.github.hachimori.kointestsample.ui.input_form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hachimori.kointestsample.R
import com.github.hachimori.kointestsample.model.Repos
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryAdapter(private val reposList: List<Repos>, private val onListItemClicked: (Repos) -> Unit) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(onListItemClicked, view)
    }

    override fun getItemCount() = reposList.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

}

class RepositoryViewHolder(private val onListItemClicked: (Repos) -> Unit, itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(repos: Repos) {
        itemView.item_repository_root.setOnClickListener {
            onListItemClicked(repos)
        }

        itemView.item_repository_name.text = repos.name
        itemView.item_repository_description.text = repos.description
    }
}
