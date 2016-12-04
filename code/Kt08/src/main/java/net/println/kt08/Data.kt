package net.println.kt08

/**
 * Created by benny on 12/3/16.
 */

/**
 * Created by benny on 11/14/16.
 */
data class SimpleResult(val status: Int, val msg: String)

data class BaseResult<Content>(val status: Int, val msg: String, val content: Content) {
    companion object {
        @JvmField
        val STATUS_OK = 0;
    }

    fun statusOK() = status == STATUS_OK
}


/**
{
song_id: 102444794,
song_name: "叫醒冬天",
singer_id: 13,
singer_name: "成龙",
album_id: 651205,
album_name: "北京申办2022年冬奥会暨北京奥林匹克音乐周优秀音乐作品",
album_pic: "http://imgcache.qq.com/music/photo/album_500/5/500_albumpic_651205_0.jpg",
play_time: 265,
play_url: "http://ws.stream.qqmusic.qq.com/M500001ALdDA13bSqB.mp3?vkey=165003144A0490A2CC4CC31E7D296A3E89B52E6F30FC80BC4F3A709BBB9C90D8A3415D269531E8998F8B666ADCE29BC08C756A323911C283&guid=1104402060&fromtag=50",
is_only: 0,
is_live: "",
is_lossless: 1,
lossless_size: 29025816,
playable: 1
},
 */

/**
 * singer_id: 13,
singer_pic: "http://imgcache.qq.com/music/photo/mid_singer_150/x/N/000nmQ1v0JGExN.jpg",
singer_name: "成龙",
album_sum: 19,
song_sum: 160,
 */
data class SingerAlbumInfo(val singer_pic: String, val singer_name: String, val album_sum: Long, val album_list: List<Album>)

/**
 * album_id: 471858,
album_desc: "成龙2014全新单曲《不再失去》。",
album_name: "不再失去",
album_pic:
 */
data class Album(val album_id: String, val album_desc: String, val album_name: String, val album_pic: String)

data class SingerFollowInfo(val singer_pic: String, val singer_name: String, val singer_id: String) {
    companion object {
        val SINGER_NAME = "SingerFollowInfo.SINGER_NAME"
        val SINGER_ID = "SingerFollowInfo.SINGER_ID"
        val SINGER_PIC = "SingerFollowInfo.SINGER_PIC"

    }

}

data class SingerFollowStatus(val singer_id: String, val follow_status: Int) {
    companion object {
        @JvmField
        val STATUS_FOLLOWED = 1
        @JvmField
        val STATUS_UNFOLLOW = 0
    }
}

data class KV(val key: String, val value: String)

data class Career(val singer_id: String, val brief_desc: String, val basic: List<KV>, val other: List<KV>) {
    fun format(): List<String> {
        val list = arrayListOf("<b>简介</b>")
        list.add(brief_desc)
        list.add("<b>基本资料</b>")
        list.addAll(basic.map { "${it.key} : ${it.value}\n" })

        other.map {
            list.add("<b>${it.key}</b>")
            list.addAll(it.value.split("\n"))
        }
        list.add("")
        return list
    }
}
