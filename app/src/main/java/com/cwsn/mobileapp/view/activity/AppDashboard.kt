package com.cwsn.mobileapp.view.activity

import android.Manifest
import android.app.Activity
import android.content.*
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.BuildConfig
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.AppDashboardLayoutBinding
import com.cwsn.mobileapp.model.home.SlideModel
import com.cwsn.mobileapp.network.APIService
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.*
import com.cwsn.mobileapp.view.adapter.SlidePanelAdapter
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.*
import com.cwsn.mobileapp.view.service.GPSTracker
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import com.cwsn.mobileapp.viewmodel.shared.SharedViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermissionUtil
import com.gun0912.tedpermission.normal.TedPermission
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<AppDashboardLayoutBinding>(), IHomeFragCallback ,
ISchoolListCallback,IResourceRoomCallback, IMonitoringFragCallback,
    ITaskActvtFragCallback,IQuestionListCallback,ISchoolVisitedFragCallback,
    ISchoolPendingFragCallback{
    private val appPref by inject<AppPreferences>()
    private val dbViewModel by viewModel<DbViewModel>()
    private lateinit var navController: NavController
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var drawerList: MutableList<SlideModel>
    private lateinit var drawerAdapter: SlidePanelAdapter
    private var locManager: LocationManager? = null
    private var gpsEnabled = false
    private var locIntent: Intent? = null
    private val sharedViewModel by viewModel<SharedViewModel>()

    val gpsEnabledLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == Activity.RESULT_OK) {
            gpsEnabled=true
            initializeLocManager()
        }
    }

    private val permissionListener = object:PermissionListener{
        override fun onPermissionGranted() {
            startLocationService()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast(getContext(), "User Denied Permission")
        }

    }

    override fun inflateLayout(layoutInflater: LayoutInflater): AppDashboardLayoutBinding {
        return AppDashboardLayoutBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@AppDashboard
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActCreate() {
        navController = Navigation.findNavController(this, R.id.app_nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navTopView,navController)
        setUpNavigationDrawer()

    }

    override fun toggleAppTopBar() {
        toggleDrawer()
    }
    private fun toggleDrawer() {
        binding.drawerLayout.openDrawer(binding.navTopView,true)
    }

    private fun setUpNavigationDrawer() {
        mDrawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerToggle?.syncState()
        mDrawerToggle?.let {
            binding.drawerLayout.addDrawerListener(it)
        }
    }

    private fun isHomeFragment(): Boolean {
        return with(navController) {
            currentDestination == graph[R.id.homeFragment]
        }
    }

    private fun loadGrievanceFragment() {
        /*val fragmentTransaction = supportFragmentManager.beginTransaction()
        val grievanceFragment = GrievanceFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer, grievanceFragment, GrievanceFragment.TAG)
        fragmentTransaction.commit()*/
    }

    private fun loadSummaryFragment() {
        /*val fragmentTransaction = supportFragmentManager.beginTransaction()
        val summaryFragment = SurveyFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer, summaryFragment, SurveyFragment.TAG)
        fragmentTransaction.commit()*/
    }
    
    private fun gotoUserProfileScreen() {
        val profileIntent = Intent(getContext(), UserProfileActivity::class.java)
        startActivity(profileIntent)
    }

    override fun onActResume() {
        //getLocalQuestions()

        drawerList= Utils.generateSlidePanelItems()
        binding.navigationView.rclvSideDrawer.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            drawerAdapter= SlidePanelAdapter(drawerList,object:IDrawerItemCallback{
                override fun onItemClicked(itemName: String) {
                    drawerNavigation(itemName)
                }
            })
            adapter = drawerAdapter
        }

        initializeLocManager()
        getLocalQuestions()

    }

    private fun initializeLocManager() {
        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)?.let {
            gpsEnabled=it
            if(gpsEnabled){
                showCustomToast(getContext(),"Fetching current location")
                checkNFetchLocation()
            }
            else{
                showGPSEnableAlert()
            }
        }
    }

    private fun showGPSEnableAlert() {
        val gpsAlertDialog = AlertDialog.Builder(getContext())
        gpsAlertDialog.setTitle("GPS Status")
        gpsAlertDialog.setMessage(
            "GPS is not enabled. GPS require to fetch location.\n Do you want to go to settings menu?")
        gpsAlertDialog.setPositiveButton("Settings",object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                gpsEnabledLauncher.launch(intent, null)
            }
        })
    }

    private fun checkNFetchLocation() {
        if (gpsEnabled) {
            startLocUpdate()
            if (TedPermissionUtil.isGranted(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {
                startLocationService()
            }
            else{
                TedPermission.create()
                    .setPermissionListener(permissionListener)
                    .setPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .setDeniedMessage(
                        "If you reject permission,you can not use this service\n" +
                                "\n" +
                                "Please turn on permissions at [Setting] > [Permission]"
                    ).check()
            }
        }
    }

    private fun startLocationService() {
        locIntent = Intent(getContext(), GPSTracker::class.java)
        startService(locIntent)
    }

    private fun stopLocationUpdates() {
        if (mLocationReceiver != null) {
            LocalBroadcastManager.getInstance(getContext())
                .unregisterReceiver(mLocationReceiver)
        }
        if (locIntent != null) {
            stopService(locIntent)
            GPSTracker().stopRunningService()
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    private fun startLocUpdate() {
        LocalBroadcastManager.getInstance(getContext())
            .registerReceiver(mLocationReceiver, IntentFilter(GPSTracker.BROADCAST_ACTION))
    }

    private val mLocationReceiver:BroadcastReceiver=object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val currLat = intent.getDoubleExtra("Latitude", 0.0)
                val currLong = intent.getDoubleExtra("Longitude", 0.0)
                if (currLat != 0.0 && currLong != 0.0) {
                    LoggerUtils.error("LOCATION","currLat $currLat , currLong $currLong")
                    appPref.updateLocationDetails(currLat,currLong)
                    getLocationAddress(currLat,currLong)
                }
            }
        }

    }

    private fun getLocationAddress(currLat: Double, currLong: Double) {
        sharedViewModel.fetchLocationAddress(generateLocationCoordinateUrl(currLat
        ,currLong)).observe(this, { response->
            when(response.status){
                Status.LOADING->{

                }
                Status.SUCCESS->{
                    response.data?.body()?.results?.let { locationResult->
                        if(locationResult.size>0){
                            val latLngResults = locationResult[0]
                            if(latLngResults.formattedAddress != null){
                                LoggerUtils.error("ADDRESS ","${latLngResults.formattedAddress}")
                                appPref.savedUserAddress(latLngResults.formattedAddress)
                                stopLocationUpdates()
                            }
                        }
                    }
                }
                Status.ERROR->{
                    response.message?.let {
                        showAppAlert(getContext(),"Alert",it,null)
                    }
                }
            }
        })
    }

    private fun generateLocationCoordinateUrl(currLat: Double, currLong: Double): String {
        return StringBuilder().append(Utils.GOOGLE_API_URL).append(APIService.LOCATION_API)
            .append("latlng=").append(currLat).append(",").append(currLong)
            .append("&sensor=").append("true").append("&key=").append(BuildConfig.LOCATION_API_KEY).toString()
    }

    private fun drawerNavigation(itemName: String) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when (itemName) {
            "Home"->{
                if (!isHomeFragment()) {
                    navController.popBackStack(R.id.homeFragment,true)
                    navController.navigate(R.id.homeFragment)
                }
            }
            "Resource Room"->{
                gotoResourceRoom()
            }
            "Monitoring"->{
                gotoMonitoring()
            }
            "Logout"->{
                appPref.performAppLogout()
                gotoLoginScreen()
            }
        }
    }

    override fun gotoHomeScreen() {
        if (!isHomeFragment()) {
            navController.popBackStack(R.id.homeFragment,true)
            navController.navigate(R.id.homeFragment)
        }
    }

    override fun gotoLoginScreen() {
        startActivity(Intent(getContext(),LoginSignUpAct::class.java))
        finish()
    }

    private fun gotoMonitoring() {
        //startActivity(Intent(getContext(),SchoolActivity::class.java))
        navController.navigateSafe(R.id.action_homeFragment_to_monitoringFragment,null,null,null)
    }

    private fun gotoResourceRoom() {
        navController.navigateSafe(R.id.action_homeFragment_to_resourceRoomFrag,null,null,null)
    }

    override fun gotoSurveyQuestionScreen(id: Int, name: String?, address: String?) {
        val bundle= Bundle()
        bundle.putInt(Utils.FORMID,id)
        bundle.putString(Utils.SCHOOLNAME,name)
        bundle.putString(Utils.SCHOOL_ADDRS,address)
        navController.navigateSafe(R.id.action_taskActivityFragment_to_questionListFragment,bundle,null,null)
    }

    override fun gotoQuestionListScreen() {
        navController.navigateSafe(R.id.action_schoolPendingFragment_to_questionListFragment,null,null,null)
    }

   fun getLocalQuestions() {
        dbViewModel.getAllQuestions().observe(this, { questions ->
            when (questions.status) {
                Status.SUCCESS -> {
                    hideProgressDialog()
                    toast("Success ${questions.data?.size}")
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    questions.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
            }
        })
    }

    override fun onUserBackPressed() {
        if (isHomeFragment()) {
            finish()
        }
        else{
            navController.navigateUp()
        }
    }

    override fun showProgress() {
        showProgressDialog()
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    override fun gotoTaskActivityScreen(name: String?) {

    }

    override fun onNavigateOptionScreen(itemName: String) {
        when(itemName){
            "School"->{
                navController.navigateSafe(R.id.action_homeFragment_to_schoolDetailsFragment,null,null,null)
            }
            "Resource Room"->{
                gotoResourceRoom()
            }
            "Monitoring"->{
                gotoMonitoring()
            }
            "School Visited"->{
                gotoSchoolVisited()
            }
            "School Pending"->{
                gotoSchoolPending()
            }
        }
    }

    private fun gotoSchoolPending() {
        navController.navigateSafe(R.id.action_homeFragment_to_schoolPendingFragment,null,null,null)
    }

    private fun gotoSchoolVisited() {
        navController.navigateSafe(R.id.action_homeFragment_to_schoolVisitedFragment,null,null,null)
    }
}