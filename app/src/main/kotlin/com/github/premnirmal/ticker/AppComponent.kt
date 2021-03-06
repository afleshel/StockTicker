package com.github.premnirmal.ticker

import com.github.premnirmal.ticker.model.HistoryProvider
import com.github.premnirmal.ticker.model.StocksProvider
import com.github.premnirmal.ticker.model.StocksStorage
import com.github.premnirmal.ticker.network.StocksApi
import com.github.premnirmal.ticker.portfolio.AddPositionActivity
import com.github.premnirmal.ticker.portfolio.EditPositionActivity
import com.github.premnirmal.ticker.portfolio.GraphActivity
import com.github.premnirmal.ticker.portfolio.PortfolioFragment
import com.github.premnirmal.ticker.portfolio.TickerSelectorActivity
import com.github.premnirmal.ticker.portfolio.drag_drop.RearrangeActivity
import com.github.premnirmal.ticker.settings.SettingsActivity
import com.github.premnirmal.ticker.widget.RemoteStockViewAdapter
import com.github.premnirmal.ticker.widget.StockWidget
import dagger.Component
import javax.inject.Singleton

/**
 * Created by premnirmal on 3/3/16.
 */
@Singleton
@Component(
    modules = arrayOf(AppModule::class)
)
interface AppComponent {

  fun inject(unlockReceiver: UnlockReceiver)

  fun inject(stocksStorage: StocksStorage)

  fun inject(tools: Tools)

  fun inject(stocksProvider: StocksProvider)

  fun inject(historyProvider: HistoryProvider)

  fun inject(stocksApi: StocksApi)

  fun inject(paranormalActivity: ParanormalActivity)

  fun inject(holder: PortfolioFragment.InjectionHolder)

  fun inject(settingsActivity: SettingsActivity)

  fun inject(tickerSelectorActivity: TickerSelectorActivity)

  fun inject(remoteStockViewAdapter: RemoteStockViewAdapter)

  fun inject(stockWidget: StockWidget)

  fun inject(updateReceiver: UpdateReceiver)

  fun inject(refreshReceiver: RefreshReceiver)

  fun inject(graphActivity: GraphActivity)

  fun inject(rearrangeActivity: RearrangeActivity)

  fun inject(addPositionActivity: AddPositionActivity)

  fun inject(editPositionActivity: EditPositionActivity)

}