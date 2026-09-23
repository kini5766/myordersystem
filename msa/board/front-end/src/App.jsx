import { useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Header from './common/Header'
import BoardList from './pages/board/BoardList'
import JoinForm from './pages/user/JoinForm'
import LoginForm from './pages/user/LoginForm'
import BoardWriteForm from './pages/board/BoardWriteForm'
import BoardDetail from './pages/board/BoardDetail'
import BoardUpdateForm from './pages/board/BoardUpdateForm'

function App() {
  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path='/home' exact={true} element={<BoardList/>}/>
          <Route path='/joinForm' exact={true} element={<JoinForm/>}/>
          <Route path='/loginForm' exact={true} element={<LoginForm/>}/>
          <Route path='/boardList' exact={true} element={<BoardList/>}/>
          <Route path='/boardWriteForm' exact={true} element={<BoardWriteForm/>}/>
          <Route path='/board/:b_num' exact={true} element={<BoardDetail/>}/>
          <Route path='/board/:b_num' exact={true} element={<BoardUpdateForm/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
