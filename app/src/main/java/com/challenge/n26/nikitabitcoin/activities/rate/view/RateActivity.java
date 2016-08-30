package com.challenge.n26.nikitabitcoin.activities.rate.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.challenge.n26.nikitabitcoin.R;
import com.challenge.n26.nikitabitcoin.activities.rate.RatePresenter;
import com.challenge.n26.nikitabitcoin.activities.rate.model.RateModel;
import com.challenge.n26.nikitabitcoin.data.repo.RateRepo;
import com.challenge.n26.nikitabitcoin.misc.ui.BarRenderer;
import com.challenge.n26.nikitabitcoin.misc.ui.GraphView;
import com.challenge.n26.nikitabitcoin.misc.ui.PoliteSwipeToRefresh;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RateActivity extends AppCompatActivity implements RateView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    PoliteSwipeToRefresh mRefreshScroll;
    @BindView(R.id.graph)
    GraphView mGraphView;
    RatePresenter mRatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);
        mRatePresenter = new RatePresenter(this);
        mRefreshScroll.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_30days:
                mRatePresenter.setPeriod(RateRepo.Timespan.month);
                break;
            case R.id.actions_60days:
                mRatePresenter.setPeriod(RateRepo.Timespan.twoMonths);
                break;
            case R.id.actions_180days:
                mRatePresenter.setPeriod(RateRepo.Timespan.halfYear);
                break;
            case R.id.actions_year:
                mRatePresenter.setPeriod(RateRepo.Timespan.year);
                break;
        }
        onRefresh();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshView(RateModel rateModel) {
        mRefreshScroll.setRefreshing(false);
        mGraphView.setValues(rateModel.getPoints());
        Toast.makeText(getApplicationContext(), "Loaded " + rateModel.getPoints().size() + " points", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mRatePresenter.loadRates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRatePresenter.loadRates();
    }
}
