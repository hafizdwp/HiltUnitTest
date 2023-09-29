package com.example.hiltunittest.data.dto

/**
 * @author hafizdwp
 * 30/08/2023
 **/
data class UnsplashResponse(
        var id: String? = "",
        var slug: String? = "",
        var created_at: String? = "",
        var updated_at: String? = "",
        var promoted_at: String? = "",
        var width: Int? = 0,
        var height: Int? = 0,
        var color: String? = "",
        var blur_hash: String? = "",
        var description: Any? = Any(),
        var alt_description: String? = "",
        var breadcrumbs: List<Any>? = listOf(),
        var urls: Urls? = Urls(),
        var links: Links? = Links(),
        var likes: Int? = 0,
        var liked_by_user: Boolean? = false,
        var current_user_collections: List<Any>? = listOf(),
        var sponsorship: Any? = Any(),
        var topic_submissions: TopicSubmissions? = TopicSubmissions(),
        var user: User? = User(),
        var exif: Exif? = Exif(),
        var location: Location? = Location(),
        var meta: Meta? = Meta(),
        var public_domain: Boolean? = false,
        var tags: List<Tag>? = listOf(),
        var tags_preview: List<TagsPreview>? = listOf(),
        var views: Int? = 0,
        var downloads: Int? = 0,
        var topics: List<Any>? = listOf()
)

data class Urls(
        var raw: String? = null,
        var full: String? = null,
        var regular: String? = null,
        var small: String? = null,
        var thumb: String? = null,
        var small_s3: String? = null
)

data class Links(
        var self: String? = null,
        var html: String? = null,
        var download: String? = null,
        var download_location: String? = null
)

data class TopicSubmissions(
        var travel: Travel? = null
)

data class User(
        var id: String? = "",
        var updated_at: String? = "",
        var username: String? = "",
        var name: String? = "",
        var first_name: String? = "",
        var last_name: String? = "",
        var twitter_username: String? = "",
        var portfolio_url: String? = "",
        var bio: String? = "",
        var location: String? = "",
        var links: LinksX? = LinksX(),
        var profile_image: ProfileImage? = ProfileImage(),
        var instagram_username: String? = "",
        var total_collections: Int? = 0,
        var total_likes: Int? = 0,
        var total_photos: Int? = 0,
        var accepted_tos: Boolean? = false,
        var for_hire: Boolean? = false,
        var social: Social? = Social()
)

data class Exif(
        var make: String? = null,
        var model: String? = null,
        var name: String? = null,
        var exposure_time: String? = null,
        var aperture: String? = null,
        var focal_length: String? = null,
        var iso: Int? = null
)

data class Location(
        var name: String? = null,
        var city: String? = null,
        var country: String? = null,
        var position: Position? = null
)

data class Meta(
        var index: Boolean? = null
)

data class Tag(
        var type: String? = "",
        var title: String? = "",
        var source: Source? = Source()
)

data class TagsPreview(
        var type: String? = null,
        var title: String? = null
)

data class Travel(
        var status: String? = null
)

data class LinksX(
        var self: String? = null,
        var html: String? = null,
        var photos: String? = null,
        var likes: String? = null,
        var portfolio: String? = null,
        var following: String? = null,
        var followers: String? = null
)

data class ProfileImage(
        var small: String? = null,
        var medium: String? = null,
        var large: String? = null
)

data class Social(
        var instagram_username: String? = null,
        var portfolio_url: String? = null,
        var twitter_username: String? = null,
        var paypal_email: Any? = null
)

data class Position(
        var latitude: Double? = null,
        var longitude: Double? = null
)

data class Source(
        var ancestry: Ancestry? = Ancestry(),
        var title: String? = "",
        var subtitle: String? = "",
        var description: String? = "",
        var meta_title: String? = "",
        var meta_description: String? = "",
        var cover_photo: CoverPhoto? = CoverPhoto()
)

data class Ancestry(
        var type: Type? = null,
        var category: Category? = null,
        var subcategory: Subcategory? = null
)

data class CoverPhoto(
        var id: String? = "",
        var slug: String? = "",
        var created_at: String? = "",
        var updated_at: String? = "",
        var promoted_at: String? = "",
        var width: Int? = 0,
        var height: Int? = 0,
        var color: String? = "",
        var blur_hash: String? = "",
        var description: String? = "",
        var alt_description: String? = "",
        var breadcrumbs: List<Any>? = listOf(),
        var urls: Urls? = Urls(),
        var likes: Int? = 0,
        var liked_by_user: Boolean? = false,
        var current_user_collections: List<Any>? = listOf(),
        var sponsorship: Any? = Any(),
        var topic_submissions: TopicSubmissionsX? = TopicSubmissionsX(),
        var premium: Boolean? = false,
        var plus: Boolean? = false,
        var user: UserX? = UserX()
)

data class Type(
        var slug: String? = null,
        var pretty_slug: String? = null
)

data class Category(
        var slug: String? = null,
        var pretty_slug: String? = null
)

data class Subcategory(
        var slug: String? = null,
        var pretty_slug: String? = null
)

data class TopicSubmissionsX(
        var nature: Nature? = null,
        var wallpapers: Wallpapers? = null,
)

data class UserX(
        var id: String? = "",
        var updated_at: String? = "",
        var username: String? = "",
        var name: String? = "",
        var first_name: String? = "",
        var last_name: String? = "",
        var twitter_username: String? = "",
        var portfolio_url: String? = "",
        var bio: String? = "",
        var location: String? = "",
        var instagram_username: String? = "",
        var total_collections: Int? = 0,
        var total_likes: Int? = 0,
        var total_photos: Int? = 0,
        var accepted_tos: Boolean? = false,
        var for_hire: Boolean? = false,
)

data class Nature(
        var status: String? = null,
        var approved_on: String? = null
)

data class Wallpapers(
        var status: String? = null,
        var approved_on: String? = null
)

data class ArtsCulture(
        var status: String? = null,
        var approved_on: String? = null
)