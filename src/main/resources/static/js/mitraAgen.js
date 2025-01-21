(()=>{
    let mainUrl = "http://localhost:8082/api/mitraAgen";
    let endPointDetail = "/detail";

    let accordionData1 = document.querySelector("#part1");
    let accordionData2 = document.querySelector("#part2");

    accordionData1.addEventListener("click", () => {
        let request = new XMLHttpRequest();
        request.open("GET", `${mainUrl}${endPointDetail}?id=${id}`);
        request.send();
        request.onload = () => {

        }
    })

    accordionData2.addEventListener("click", () => {
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}${endPointDetail}?id=${id}`);
            request.send();
            request.onload = () => {

            }
        })

    let populateDetail = (detail) => {
        let identitasField = document.querySelector("#identitas");
        let namaField = document.querySelector("#nama");
        let kelaminField = document.querySelector("#kelamin");
        let npwpField = document.querySelector("#NPWP");
        let alamatField = document.querySelector("#address");
        let kelIdenField = document.querySelector("#kelurahanIdentitas");
        let kodPosIdenField = document.querySelector("#kodeposIdentitas");
        let kecIdenField = document.querySelector("#kecamatanIdentitas");
        let kabIdenField = document.querySelector("#kotakabupatenIdentitas");
        let provIdenField = document.querySelector("#provinsiIdentitas");
        let kelDomField = document.querySelector("#kelurahanDomisili");
        let kodPosDomField = document.querySelector("#kodeposDomisili");
        let kecDomField = document.querySelector("#kecamatanDomisili");
        let kabDomField = document.querySelector("#kotakabupatenDomisili");
        let provDomField = document.querySelector("#provinsiDomisili");
        let tempatLahirField = document.querySelector("#tempatlahir");
        let tanggalLahirField = document.querySelector("#tanggalLahir");
        let nomorTelpField = document.querySelector("#nomortelepon");
        let nomorHPField = document.querySelector("#nomorhandphone");


    }
})