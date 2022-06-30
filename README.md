#  Simple Chatting Program in java

> ## 1. Client
 1) 소켓을 만든다.
 2) 서버에게 요청을 보내 connection 한다.
 3) RoomAdmin 실행 (채팅 관리자)
 4) 쓰레드로 서버에서 데이터 수신 될때마다 체크
 5) 서버와 데이터 송/수신
 6) 연결 종료
 
> ## 2. Server
 1) 서버 socket생성 및 ip주소와 port 소켓에 바인딩
 2) listen상태로 클라이언트 요청 wait 후 데이터 송수신을 위하여 클라이언트에 소켓 리턴
 3) 쓰레드로 클라이언트에서 받은 메세지를 해당 방에 있는 사람들 한테만 뿌린다. (PORT 번호 KEY)
 4) 연결 종료
 
> ## 3. Precautions
 - RoomAdmin.java, IdFrame.java 내 이미지 절대 경로이므로 수정해야 한다.
