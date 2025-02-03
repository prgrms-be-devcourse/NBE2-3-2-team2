package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminMovieJpaRepository;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminMovieServiceImpl {
    @Value("${MOVIE_API_KEY}")
    private String API_KEY;
    //영화 목록 (영화 이름을 검색해 영화 코드를 가져옴 movieNm -> movieCd)
    private static final String MOVIE_LIST_URL = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml";
    //영화 상세정보 (movieCd로 검색)
    private static final String MOVIE_INFO_URL = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml";

    private final RestTemplate restTemplate;

    @Autowired
    private AdminMovieJpaRepository adminMovieJpaRepository;

    public AdminMovieServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    // 영화 목록에서 movieCd 검색
    public String getMovieCodeByName(String movieNm) {
        try {
            String url = MOVIE_LIST_URL + "?key=" + API_KEY + "&movieNm=" + movieNm;
            String response = restTemplate.getForObject(url, String.class);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(response.getBytes("UTF-8")));

            NodeList movieNodes = document.getElementsByTagName("movie");
            String latestMovieCd = null;
            for (int i = 0; i < movieNodes.getLength(); i++) {
                Node movieNode = movieNodes.item(i);
                NodeList childNodes = movieNode.getChildNodes();
                String currentMovieCd = null;
                String currentMovieNm = null;

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);

                    if ("movieCd".equals(childNode.getNodeName())) {
                        currentMovieCd = childNode.getTextContent();
                    } else if ("movieNm".equals(childNode.getNodeName())) {
                        currentMovieNm = childNode.getTextContent();
                    }
                }

                if (movieNm.equals(currentMovieNm)) {
                    latestMovieCd = currentMovieCd;
                }
            }
            return latestMovieCd;
        } catch (Exception e) {
            System.out.println("Error(getMovieCodeByName) : " + e.getMessage());
        }
        return null;
    }


    // 영화 상세정보 조회
    public Map<String, Object> getMovieInfoByCode(String movieCd) {
        Map<String, Object> movieDetails = new LinkedHashMap<>();
        try {
            String url = MOVIE_INFO_URL + "?key=" + API_KEY + "&movieCd=" + movieCd;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("movieInfo");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;

                movieDetails.put("movieName", getTagValue("movieNm", eElement));
                movieDetails.put("movieCode", getTagValue("movieCd", eElement));
                movieDetails.put("openDate", getTagValue("openDt", eElement));

                NodeList auditList = eElement.getElementsByTagName("audit");
                if (auditList.getLength() > 0) {
                    Element lastAuditElement = (Element) auditList.item(auditList.getLength() - 1);
                    String watchGradeNm = getTagValue("watchGradeNm", lastAuditElement);
                    movieDetails.put("rating", watchGradeNm);
                } else {
                    movieDetails.put("rating", "미등록");
                }

                movieDetails.put("genreName", getTagValue("genres", "genre","genreNm", eElement));
                movieDetails.put("runtime", getTagValue("showTm", eElement));
                movieDetails.put("directorName", getTagValue("directors", "director","peopleNm", eElement));

                NodeList companyList = eElement.getElementsByTagName("company");
                for (int i = 0; i < companyList.getLength(); i++) {
                    Element companyElement = (Element) companyList.item(i);
                    String companyPartNm = getTagValue("companyPartNm", companyElement);
                    if ("배급사".equals(companyPartNm)) {
                        String companyNm = getTagValue("companyNm", companyElement);
                        movieDetails.put("companys", companyNm);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error(getMovieInfoByCode) : " + e.getMessage());
        }
        return movieDetails;
    }

    public List<Movie> findAllMovies(){
        List<Movie> movies = adminMovieJpaRepository.findAllMovies();

        return movies;
    }

    public Page<Movie> findAllMovieswithPage(Pageable pageable) {
        return adminMovieJpaRepository.findAllMovieswithPage(pageable);
    }

    public List<Movie> findMovieByName(String movieNm) {
        List<Movie> movies = adminMovieJpaRepository.findMovieByName(movieNm);
        return movies;
    }

    public void addMovie(Movie movie) {
        movie.setId(null);
        movie.setSalesAcc("0");
        movie.setAudiAcc("0");

        adminMovieJpaRepository.save(movie);
    }

    // 영화 검색
    public Movie findMovieById(Long id) {
        return adminMovieJpaRepository.findById(id).orElse(null);
    }

    // 영화 수정
    public void updateMovie(Movie movie) {
        Movie existingMovie = adminMovieJpaRepository.findById(movie.getId())
                .orElseThrow(() -> new IllegalArgumentException("영화가 존재하지 않습니다. ID: " + movie.getId()));

        existingMovie.setPosterImageUrl(movie.getPosterImageUrl());
        existingMovie.setStillImageUrl(movie.getStillImageUrl());
        existingMovie.setStatus(movie.getStatus());

        adminMovieJpaRepository.save(existingMovie);
    }

    // 영화 삭제
    public void deleteMovieById(Long movieId) {
        adminMovieJpaRepository.deleteById(movieId);
    }


    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, Element eElement) {
        String result = "";
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        result = nlList.item(0).getTextContent();

        return result;
    }

    // 자식 tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, String childTag, Element eElement) {
        String result = "";
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {
            result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
        }

        return result;
    }

    // 손자 tag값의 정보를 가져오는 함수
    public static String getTagValue(String parentTag, String childTag, String grandChildTag, Element eElement) {
        StringBuilder result = new StringBuilder();

        NodeList parentNodeList = eElement.getElementsByTagName(parentTag);

        for (int i = 0; i < parentNodeList.getLength(); i++) {
            Node parentNode = parentNodeList.item(i);

            if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element parentElement = (Element) parentNode;
                NodeList childNodeList = parentElement.getElementsByTagName(childTag);

                for (int j = 0; j < childNodeList.getLength(); j++) {
                    Node childNode = childNodeList.item(j);

                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        NodeList grandChildNodeList = childElement.getElementsByTagName(grandChildTag);
                        for (int k = 0; k < grandChildNodeList.getLength(); k++) {
                            Node grandChildNode = grandChildNodeList.item(k);
                            if (grandChildNode.getNodeType() == Node.ELEMENT_NODE) {
                                result.append(grandChildNode.getTextContent()).append(" ");
                            }
                        }
                    }
                }
            }
        }
        return result.toString().trim();
    }
}
