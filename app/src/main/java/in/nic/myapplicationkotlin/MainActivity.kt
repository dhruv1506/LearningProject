package `in`.nic.myapplicationkotlin

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import java.net.URI


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    val TAG="MainActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        when
        {
            ContextCompat.checkSelfPermission(this@MainActivity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ==PackageManager.PERMISSION_GRANTED->
            {
                val url="https://cdn.shopify.com/s/files/1/2081/8163/files/001-HIDE-AND-SEEK-Free-Childrens-Book-By-Monkey-Pen.pdf?v=1589846897"
                val request = DownloadManager.Request(Uri.parse(url))
                        .setTitle("File")
                        .setDescription("Downloading...")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedOverMetered(true)
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
            }
            else->
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions( arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),101)
                }
            }
        }

        /* var tvDummy: TextView =findViewById(R.id.tvdummy)
          GlobalScope.launch {Dispatchers.IO

              Log.d(TAG,"Hello coroutine from seperate thred ${Thread.currentThread().name}")

              var call1=doNetworking1();


              Log.d(TAG,call1)
              var call2=doNetworking2();
              Log.d(TAG,call2)
              var call3=doNetworking2();
              Log.d(TAG,call3)

              withContext(Dispatchers.Main)
              {
                  tvDummy.text=call1
              }



          }
          Log.d(TAG,"Hello Main thread from seperate thrad ${Thread.currentThread().name}")*/

            }

    private fun hasExternalStoragePermission()=
        ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED

    private  fun requestPermission()
    {
        var permissionToReqest = mutableListOf<String>()
            if (!hasExternalStoragePermission())
                permissionToReqest.add(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)

        if (permissionToReqest.isEmpty())
            ActivityCompat.requestPermissions(this,permissionToReqest.toTypedArray(),101)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==101 && grantResults.isNotEmpty())
            for (i in grantResults.indices)
            {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("request permission","${grantResults[0]} permission granted")
                }
            }
    }



   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
*/    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            101->{



            }

        }
        if (requestCode==101)
        {

        }
    }*/

    suspend fun doNetworking1():String
    {
        delay(3000L)
        Log.d(TAG,"doNetworking1 ${Thread.currentThread().name}")
        return "Networking call 1"
    }
    suspend fun doNetworking2():String
    {   delay(3000L)
        Log.d(TAG,"doNetworking2 ${Thread.currentThread().name}")
        return "Networking call 2"
    }

    suspend fun doNetworking3():String
    {
        delay(3000L)
        Log.d(TAG,"doNetworking1 ${Thread.currentThread().name}")
        return "Networking call 3"
    }
}