import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Header from './common/Header'
import ProductList from './pages/product/ProductList'
import ProductWriteForm from './pages/product/ProductWriteForm'
import ProductUpdateForm from './pages/product/ProductUpdateForm'
import ProductDetail from './pages/product/ProductDetail'
import MyProductList from './pages/product/MyProductList'
import ProductSearch from './pages/product/ProductSearch'
import OrderingList from './pages/ordering/OrderingList'
import JoinPage from './pages/user/JoinPage'
import LoginPage from './pages/user/LoginPage'
import CookiePage from './pages/user/CookiePage'
import UserPage from './pages/user/UserPage'

function App() {
  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" exact={true} element={<ProductList/>}/>

          <Route path="/product" exact={true} element={<ProductWriteForm/>}/>
          <Route path="/product/:productId/modify" exact={true} element={<ProductUpdateForm/>}/>
          <Route path="/product/:productId" exact={true} element={<ProductDetail/>}/>
          <Route path="/myProductList" exact={true} element={<MyProductList/>}/>
          <Route path="/product/search/:search" exact={true} element={<ProductSearch/>}/>
          <Route path="/orderingList" exact={true} element={<OrderingList/>}/>

          <Route path="/join" element={<JoinPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/cookie" element={<CookiePage />} />
          <Route path="/user" element={<UserPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
