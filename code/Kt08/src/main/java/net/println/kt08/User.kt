package net.println.kt08

/**
 * Created by benny on 11/1/16.
 */

/**
 * {
login: "bindy",
id: 849661,
avatar_url: "https://avatars.githubusercontent.com/u/849661?v=3",
gravatar_id: "",
url: "https://api.github.com/users/bindy",
html_url: "https://github.com/bindy",
followers_url: "https://api.github.com/users/bindy/followers",
following_url: "https://api.github.com/users/bindy/following{/other_user}",
gists_url: "https://api.github.com/users/bindy/gists{/gist_id}",
starred_url: "https://api.github.com/users/bindy/starred{/owner}{/repo}",
subscriptions_url: "https://api.github.com/users/bindy/subscriptions",
organizations_url: "https://api.github.com/users/bindy/orgs",
repos_url: "https://api.github.com/users/bindy/repos",
events_url: "https://api.github.com/users/bindy/events{/privacy}",
received_events_url: "https://api.github.com/users/bindy/received_events",
type: "User",
site_admin: false
},
 */

data class User(val login: String, val id: Long, val avatar_url: String, val url: String)