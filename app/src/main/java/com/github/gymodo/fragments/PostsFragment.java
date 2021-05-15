package com.github.gymodo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.gymodo.R;
import com.github.gymodo.adapters.PostsAdapter;
import com.github.gymodo.social.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment {

    List<Post> postsList;
    PostsAdapter postsAdapter;
    FirebaseAuth firebaseAuth;

    public PostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postsList = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), postsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        FloatingActionButton btnAddNewPost = view.findViewById(R.id.btnAddNewPost);
        RecyclerView recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerViewPosts.setAdapter(postsAdapter);

        firebaseAuth = FirebaseAuth.getInstance();

        Post.listAllOrdered().addOnSuccessListener(posts -> {
            postsList.clear();
            postsList.addAll(posts);
            postsAdapter.notifyDataSetChanged();
        });

        btnAddNewPost.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_postsFragment2_to_newPostFragment);
        });

        return view;
    }
}