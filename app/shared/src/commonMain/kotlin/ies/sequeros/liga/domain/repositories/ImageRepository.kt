package ies.sequeros.liga.domain.repositories

import ies.sequeros.liga.domain.valueobjects.ImageURL
import kotlinx.coroutines.flow.Flow

interface  ImageRepository {
     fun getAll(): Flow<List<ImageURL>>
}