[![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[ ![Download](https://api.bintray.com/packages/rizlee/permission-handler/permission-handler/images/download.svg?version=1.0.0) ](https://bintray.com/rizlee/permission-handler/permission-handler/1.0.0/link)

# Usage
#### Gradle
```xml
implementation "com.rizlee:permission-handler:$last_version"
```

#### Kotlin
```kotlin
private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
    }

    private fun requestPermissions() =
        PermissionHandler.requestPermission(this, { permissionsGranted() }, PERMISSIONS)

    private fun permissionsGranted() =
        Toast.makeText(applicationContext, "permissions granted", Toast.LENGTH_SHORT).show()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionHandler.permissionsResult(
            this,
            PERMISSIONS,
            requestCode,
            grantResults,
            { permissionsGranted() },
            { Toast.makeText(applicationContext, "permissions unsuccess", Toast.LENGTH_SHORT).show() },
            { Toast.makeText(applicationContext, "permissions error", Toast.LENGTH_SHORT).show() }
        )
    }
```
