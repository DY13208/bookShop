function editCart(cartItemId,buyCount){
    window.location.href="cart.do?operate=editCart&cartItemId="+cartItemId+"&buyCount="+buyCount;
}
window.onload=function (){
    let vue = new Vue({
        el:"#cart_div",
        data:{
            cart:{},
            disabled:false
        },
        methods:{
            getCart:function (){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'getCart'
                    }

                }).then(function (value) {
                        let CartData = value.data;
                        vue.cart = CartData;
                }).catch(function (reason) {
                    
                });
            },
            editCart:function (cartItemId,buyCount) {
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'editCart',
                        cartItemId:cartItemId,
                        buyCount:buyCount
                    }
                }).then(function (value) {

                    console.log("___"+buyCount);

                    if(buyCount===0){
                        vue.disabled=true;
                        buyCount=1;
                    }else {
                        vue.disabled=false;
                        vue.getCart();
                    }
                }).catch(function (reason) {

                });


            }
        }
        ,
        mounted:function (){
                this.getCart();
            }

    });
}