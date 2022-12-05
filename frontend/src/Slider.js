import {useRef, useState} from "react"

const Slider = () => {

    const [items, setItems] = useState(["https://phonoteka.org/uploads/posts/2021-06/1624512941_36-phonoteka_org-p-porshe-panamera-oboi-krasivo-40.jpg","https://2.allegroimg.com/original/038521/db9ff67440869898b7970bfa7632/Zwrotnica-prawa-Panamera-2017-Producent-czesci-Porsche-OE","https://www.car72.ru/images/photo/2017/05/41219.jpg","https://static.life.ru/9b33d387fc5771bfea190927a4649529.jpeg","https://automobile-zip.ru/wp-content/uploads/c/b/6/cb6a9e05e16dc0f779643cae7441cd9e.jpeg","https://i.ytimg.com/vi/ZPeeSzG2oe0/maxresdefault.jpg"])
    const [st, setSt] = useState(["Пятидверный спортивный фастбэк класса Гран Туризмо.","Может ли мечта быть логичной?","Cнижено трение и уменьшена масса.","Увеличен КПД двигателей.","Выпуск и продажи автомобиля начались в 2009 году.","Сохранение стиля. Сохранение ценности."])
    const slider = useRef(null)
    let slider_pos = 0
    const setStyle = (i) => {
        let rotate = slider_pos + 1 - i
        if(rotate <= -1 || rotate >= 1){
            return `transform: translateX(${-slider_pos * 100}%) scale(0.7, 0.7); filter: blur(2px);`
        }
        else{
            return `transform: translateX(${-slider_pos * 100}%) rotateY(0deg) scale(1.3, 1.3); box-shadow: 0 10px 40px rgba(120, 27, 47, 0.67); border-radius: 10px; z-index: 2;`
        }
    }
    const nextHandler = () => {
        if(slider_pos + 1 > 0){
            slider_pos -= 1
            let i = 0
            slider.current.childNodes.forEach((el) => {
                el.style = setStyle(i)
                i += 1
            })
        }

    }
    const prevHandler = () => {
        if(slider_pos + 1 < items.length - 1){
            slider_pos += 1
            let i = 0
            slider.current.childNodes.forEach((el) => {
                el.style = setStyle(i)
                i += 1
            })
        }
    }

    return (
        <div className="Slider">
            <div className="track" ref={slider}>
                {items.map((src, key) => {
                    return (
                        <div key={key} className="item">
                            <img className="slider-img" src={src}/>
                            <div className="slider-content">
                                <h5>PORCHE PANAMERA</h5>
                                <p>{st[key]}</p>
                            </div>
                        </div>
                    )
                })}
            </div>
            <button className="slider-btn slider-btn-prev" onClick={prevHandler}>{`<`}</button>
            <button className="slider-btn slider-btn-next" onClick={nextHandler}>{`>`}</button>
        </div>
    )
}
export default Slider
