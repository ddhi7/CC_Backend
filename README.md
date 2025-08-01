# CC_Backend_MSA
# 🎯 Monolithic to MSA 전환 프로젝트

개인적으로 진행한 기존 모놀리식(Monolithic) 프로젝트를 MSA 구조로 리팩토링한 프로젝트입니다.  
학습을 목적으로 MSA로 전환하는 경험에 초점을 맞추고 메시지 큐(Kafka/RabbitMQ 등)와 **쿠버네티스(Kubernetes)** 환경에서의 배포까지 경험해보
---


## 📌 프로젝트 목표

- 모놀리식 구조 → MSA 아키텍처로 분해 및 재설계
- 서비스 간 통신을 REST + 메시지 브로커 방식으로 구현
- 쿠버네티스 + 도커 로 배포 경험
