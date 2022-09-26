package developer.abdulaziz.myrestapi.Utils

data class MyResource<out T>(val myStatus: MyStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(): MyResource<T> = MyResource(MyStatus.LOADING, null, "Loading...")
        fun <T> error(message: String?): MyResource<T> = MyResource(MyStatus.ERROR, null, message)
        fun <T> success(data: T): MyResource<T> = MyResource(MyStatus.SUCCESS, data, null)
    }
}