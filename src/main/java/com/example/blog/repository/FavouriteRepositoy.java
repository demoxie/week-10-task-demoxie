package com.example.blog.repository;

import com.example.blog.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepositoy extends JpaRepository<Favourites,Long> {
    Favourites findFavouritesByFavouriteID(Long favouriteID);
    Favourites findFavouritesByFavouriteName(String favouriteName);
}
