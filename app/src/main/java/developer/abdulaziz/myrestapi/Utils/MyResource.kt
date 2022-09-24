package developer.abdulaziz.myrestapi.Utils

data class MyResource<out T>(val myStatus: MyStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(message: String): MyResource<T> = MyResource(MyStatus.LOADING, null, message)
        fun <T> error(message: String?): MyResource<T> = MyResource(MyStatus.ERROR, null, message)
        fun <T> success(data: T): MyResource<T> = MyResource(MyStatus.SUCCESS, data, null)
    }
}