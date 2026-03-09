import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [stats, setStats] = useState(null);
  const [logs, setLogs] = useState([]);

  // 데이터 불러오기
  useEffect(() => {
    // 1. 대시보드 요약 통계 가져오기
    fetch("http://localhost:8080/internal/api/logs/dashboard")
      .then(res => {
        if (!res.ok) throw new Error("대시보드 데이터를 가져오는데 실패했습니다.");
        return res.json();
      })
      .then(data => setStats(data))
      .catch(err => console.error(err));

    // 2. 최근 로그 리스트 가져오기 (첫 페이지 10개)
    fetch("http://localhost:8080/internal/api/logs?size=10")
      .then(res => {
        if (!res.ok) throw new Error("로그 데이터를 가져오는데 실패했습니다.");
        return res.json();
      })
      .then(data => setLogs(data.content))
      .catch(err => console.error(err));
  }, []);

  if (!stats) return <div className="loading">백엔드 서버 연결 확인 중...</div>;

  return (
    <div className="dashboard-container">
      <header>
        <h1>ClientOps Admin Dashboard</h1>
        <p className="subtitle">실시간 API 요청 모니터링 시스템</p>
      </header>

      {/* 상단 통계 카드 섹션 */}
      <div className="stats-grid">
        <div className="card">
          <span className="card-label">전체 요청 수</span>
          <p className="number">{stats.totalRequests.toLocaleString()}</p>
        </div>
        <div className="card">
          <span className="card-label">에러 발생 수</span>
          <p className="number red">{stats.errorRequests.toLocaleString()}</p>
        </div>
        <div className="card">
          <span className="card-label">평균 에러율</span>
          <p className="number">{stats.errorRate.toFixed(2)}%</p>
        </div>
      </div>

      {/* 하단 최근 로그 리스트 섹션 */}
      <section className="log-section">
        <h2>최근 API 요청 로그</h2>
        <table className="log-table">
          <thead>
            <tr>
              <th>요청 시간</th>
              <th>메서드</th>
              <th>엔드포인트</th>
              <th>상태코드</th>
              <th>고객사명</th>
            </tr>
          </thead>
          <tbody>
            {logs.length > 0 ? logs.map(log => (
              <tr key={log.id}>
                <td>{new Date(log.requestTime).toLocaleString()}</td>
                <td><span className={`method-badge ${log.httpMethod}`}>{log.httpMethod}</span></td>
                <td className="endpoint">{log.endpoint}</td>
                <td>
                  <span className={`status-badge ${log.statusCode >= 400 ? 'error' : 'success'}`}>
                    {log.statusCode}
                  </span>
                </td>
                <td>{log.clientName}</td>
              </tr>
            )) : (
              <tr>
                <td colSpan="5" style={{textAlign: 'center', padding: '20px'}}>로그 데이터가 없습니다.</td>
              </tr>
            )}
          </tbody>
        </table>
      </section>
    </div>
  )
}

export default App