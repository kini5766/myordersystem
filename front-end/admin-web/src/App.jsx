import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Header from './common/Header'
import LoginPage from './pages/user/LoginPage'
import UserPage from './pages/user/UserPage'
import BoardList from './pages/board/BoardList'
import BoardWriteForm from './pages/board/BoardWriteForm'
import BoardUpdateForm from './pages/board/BoardUpdateForm'

function App() {
  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path='/login' element={<LoginPage/>}/>
          <Route path="/user" element={<UserPage />} />
          <Route path='/board' element={<BoardList/>}/>
          <Route path='/board/write' element={<BoardWriteForm/>}/>
          <Route path='/board/:board_no' element={<BoardUpdateForm/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
