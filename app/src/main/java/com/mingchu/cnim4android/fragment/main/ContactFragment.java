package com.mingchu.cnim4android.fragment.main;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingchu.cnim4android.R;
import com.mingchu.cnim4android.activitys.MessageActivity;
import com.mingchu.cnim4android.fragment.search.SearchUserFragment;
import com.mingchu.common.app.BaseFragment;
import com.mingchu.common.widget.EmptyView;
import com.mingchu.common.widget.custom.PortraitView;
import com.mingchu.common.widget.recycler.RecyclerAdapter;
import com.mingchu.factory.model.card.UserCard;
import com.mingchu.factory.model.db.User;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private RecyclerAdapter<User> mRecyclerAdapter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerAdapter = new RecyclerAdapter<User>() {
            @Override
            protected int getItemViewType(int position, User data) {
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {


                return new ContactFragment.ViewHolder(root);
            }
        };

        mRecyclerView.setAdapter(mRecyclerAdapter);

        /**
         * Item事件监听方法
         */
        mRecyclerAdapter.setAdapterItemClickListener(new RecyclerAdapter.AdapterItemClickListener<User>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, final User data) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到聊天界面
                        MessageActivity.show(getContext(),data);
                    }
                });
            }

            @Override
            public void onLongItemClick(RecyclerAdapter.ViewHolder holder, User data) {

            }
        });

        //初始化占位布局
        mEmptyView.bind(mRecyclerView);
        setPlaceHolderView(mEmptyView);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<User> {

        @BindView(R.id.iv_portrait)
        PortraitView mIvPortrait;

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_desc)
        TextView mTvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User data) {

            mIvPortrait.setup(Glide.with(ContactFragment.this), data);

            if (!TextUtils.isEmpty(data.getName())) {
                mTvName.setText(data.getName());
                mTvName.setVisibility(View.VISIBLE);
            } else {
                mTvName.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(data.getDesc())) {
                mTvDesc.setText(data.getDesc());
                mTvDesc.setVisibility(View.VISIBLE);
            } else {
                mTvDesc.setVisibility(View.INVISIBLE);
            }

        }
    }


}
