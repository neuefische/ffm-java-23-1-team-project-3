
export type Book = {
    id         : string
    title      : string
    author     : string
    description: string
    publisher  : string
    isbn       : string
    coverUrl   : string
    favorite   : boolean
}

export type UserInfos = {
    isAuthenticated : boolean
    id          : string
    login       : string
    name        : string
    location    : string
    url         : string
    avatar_url  : string
}
