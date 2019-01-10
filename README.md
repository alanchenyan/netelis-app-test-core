### Netelis App Test Core

### 步骤
1、发布release版本

2、打开网址：https://jitpack.io

3、搜索 alanchenyan/netelis-app-test-core

4、可选择 gradle、maven、sot、leingen进行构建

### Gradle

Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```
dependencies {
    implementation 'com.github.alanchenyan:netelis-app-test-core:V1.0.2'
}
```
