package com.ablec.myarchitecture.gridtopager.fragment

import android.os.Bundle
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.gridtopager.GridActivity
import com.ablec.myarchitecture.gridtopager.adapter.GridAdapter
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * A fragment for displaying a grid of images.
 */
class GridFragment : Fragment(R.layout.fragment_grid) {

    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.adapter = GridAdapter(this)
        prepareTransitions()
    }


    /**
     * Prepares the shared element transition to the pager fragment, as well as the other transitions
     * that affect the flow.
     */
    private fun prepareTransitions() {
        val transition:android.transition.Transition = android.transition.TransitionInflater.from(
            context
        ).inflateTransition(R.transition.shared_element_transition)
        requireActivity().window.reenterTransition = transition
        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        requireActivity().setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder = recyclerView
                        ?.findViewHolderForAdapterPosition(GridActivity.currentPosition) ?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] =
                        selectedViewHolder.itemView.findViewById(R.id.card_image)
                }
            })

    }

    fun scroll(){
        requireActivity().postponeEnterTransition();
        recyclerView?.scrollToPosition(GridActivity.currentPosition)
        recyclerView?.doOnPreDraw {
            recyclerView?.requestLayout()
            requireActivity().startPostponedEnterTransition();
        }
    }
}
