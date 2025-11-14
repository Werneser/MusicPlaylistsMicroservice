package org.example.repository

import org.example.entity.DownloadCount
import org.springframework.data.repository.CrudRepository

interface DownloadCountRepository: CrudRepository<DownloadCount, Long>  {

}