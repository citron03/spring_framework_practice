package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);

        Movie movie = (Movie) entityMap.get("movie");

        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        // 데이터베이스에서 임의로 이미지를 삭제하여 오류가 발생하였었다.
        // 이미지에 할당된 영화의 번호에 데이터가 존재하지 않아 오류가 발생.

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie) arr[0] ,
                (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3]

        ));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0];
        // Movie entity는 맨 앞 인덱스에 존재한다. 그리고 모든 row가 같은 값을 가진다.

        List<MovieImage> movieImageList = new ArrayList<>();
        // 영화의 이미지 갯수만큼 MovieImage 객체가 필요하다.

        result.forEach(arr -> {

            MovieImage movieImage = (MovieImage)arr[1];
            movieImageList.add(movieImage);

        });

        // row들 중 이미지만 다름. - 여러개의 이미지를 표현

        Double avg = (Double) result.get(0)[2];
        // 평균 평점 - 모든 row가 동일한 값을 가짐.

        Long reviewCnt = (Long) result.get(0)[3];
        // 리뷰 개수 - 모든 Row가 동일한 값.

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);

    }

}
