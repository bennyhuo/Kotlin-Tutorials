package net.println.kt08

/**
 * Created by benny on 10/28/16.
 */

/**
login: "xiazdong",
id: 1818838,
avatar_url: "https://avatars.githubusercontent.com/u/1818838?v=3",
gravatar_id: "",
url: "https://api.gitHub.com/users/xiazdong",
html_url: "https://gitHub.com/xiazdong",
followers_url: "https://api.gitHub.com/users/xiazdong/followers",
following_url: "https://api.gitHub.com/users/xiazdong/following{/other_user}",
gists_url: "https://api.gitHub.com/users/xiazdong/gists{/gist_id}",
starred_url: "https://api.gitHub.com/users/xiazdong/starred{/owner}{/repo}",
subscriptions_url: "https://api.gitHub.com/users/xiazdong/subscriptions",
organizations_url: "https://api.gitHub.com/users/xiazdong/orgs",
repos_url: "https://api.gitHub.com/users/xiazdong/repos",
events_url: "https://api.gitHub.com/users/xiazdong/events{/privacy}",
received_events_url: "https://api.gitHub.com/users/xiazdong/received_events",
type: "User",
site_admin: false
 */

data class User(val login: String, val id: Int, val avatar_url: String, val url: String)
