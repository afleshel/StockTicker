package com.github.premnirmal.ticker

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.premnirmal.ticker.mock.Mocker
import org.junit.After
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.util.ActivityController



/**
 * Created by premnirmal on 3/22/17.
 */
abstract class BaseActivityUnitTest<T : FragmentActivity> @JvmOverloads constructor(
    private val mClass: Class<T>, private val mAutoCreate: Boolean = true) : BaseUnitTest() {

  lateinit protected var mActivity: T
  lateinit protected var mController: ActivityController<T>

  @Before
  fun beforeTestCreate() {
    mController = Robolectric.buildActivity(mClass)
    if (mAutoCreate) {
      createActivity()
    }
  }

  @After
  fun afterTestDestroy() {
    mController.pause().stop().destroy()
    Mocker.clearMocks()
  }

  protected fun setIntent(intent: Intent) {
    assertNull("Cannot set intent after starting the activity", mActivity)
    mController.withIntent(intent)
  }

  protected fun createWithStringExtra(key: String, value: String) {
    val intent = Intent(RuntimeEnvironment.application, mClass)
    intent.putExtra(key, value)
    setIntent(intent)
  }

  protected fun createWithExtras(extras: Bundle) {
    val intent = Intent(RuntimeEnvironment.application, mClass)
    intent.putExtras(extras)
    setIntent(intent)
  }

  protected fun createActivity() {
    mActivity = mController.setup().get()
  }

  protected fun findFragment(tag: String): Fragment {
    return mActivity.supportFragmentManager.findFragmentByTag(tag)
  }

  protected fun verifyVisibleFragment(tag: String): Fragment {
    val fragment = findFragment(tag)
    assertNotNull(fragment)
    assertTrue(fragment.isAdded)
    assertTrue(fragment.isVisible)
    return fragment
  }
}