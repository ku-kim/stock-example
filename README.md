# 재고시스템으로 알아보는 동시성이슈

이 저장소는 [최상용님의 '재고시스템으로 알아보는 동시성이슈 해결방법'](https://www.inflearn.com/course/%EB%8F%99%EC%8B%9C%EC%84%B1%EC%9D%B4%EC%8A%88-%EC%9E%AC%EA%B3%A0%EC%8B%9C%EC%8A%A4%ED%85%9C) 인프런 강의 소스 기반으로 작성되었습니다.

원본 저장소는 [이곳](https://github.com/sangyongchoi/stock-example/blob/main/README.md)에서 확인하실 수 있습니다.

---

# Docker-compose : MySQL 8.0 & Redis
```shell
## Path : 최상위 디렉토리
# up
docker-compose -f docker/docker-compose.yml up -d

# down
docker-compose -f docker/docker-compose.yml down

# mysql 연결
docker exec -it stock_example_mysql bash # mysql 컨테이너 bash 연결
> mysql -u user1 -p  # 컨테이너 배쉬 안에서 실행 (password 1234)

# redis 연결
docker exec -it stock_example_redis redis-cli

```

---

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fku-kim%2Fstock-example&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
