(()=>{
    let mainUrl = "http://localhost:8082/api/mitraAgen";
    let endPointDetail = "/detail";

    let accordionData1 = document.querySelector("#part1");
    let accordionData2 = document.querySelector("#part2");
    let id = document.querySelector("#id").textContent;

    accordionData1.addEventListener("click", () => {
        let request = new XMLHttpRequest();
        request.open("GET", `${mainUrl}${endPointDetail}?id=${id}`);
        request.send();
        request.onload = () => {
            populateDetail1(JSON.parse(request.response));
        }
    })

    accordionData2.addEventListener("click", () => {
            let request = new XMLHttpRequest();
            request.open("GET", `${mainUrl}${endPointDetail}?id=${id}`);
            request.send();
            request.onload = () => {
                populateDetail2(JSON.parse(request.response));
            }
        })

    let populateDetail1 = (detail) => {
        let identitasField = document.querySelector("#identitas");
        let namaField = document.querySelector("#nama");
        let kelaminField = document.querySelector("#kelamin");
        let npwpField = document.querySelector("#NPWP");
        let alamatIdenField = document.querySelector("#alamatIdentitas");
        let kelIdenField = document.querySelector("#kelurahanIdentitas");
        let kodPosIdenField = document.querySelector("#kodeposIdentitas");
        let kecIdenField = document.querySelector("#kecamatanIdentitas");
        let kabIdenField = document.querySelector("#kotakabupatenIdentitas");
        let provIdenField = document.querySelector("#provinsiIdentitas");
        let alamatSamaField = document.querySelector("#alamatSama");
        let alamatDomField = document.querySelector("#alamatDomisili");
        let kelDomField = document.querySelector("#kelurahanDomisili");
        let kodPosDomField = document.querySelector("#kodeposDomisili");
        let kecDomField = document.querySelector("#kecamatanDomisili");
        let kabDomField = document.querySelector("#kotakabupatenDomisili");
        let provDomField = document.querySelector("#provinsiDomisili");
        let tempatLahirField = document.querySelector("#tempatlahir");
        let tanggalLahirField = document.querySelector("#tanggallahir");
        let nomorTelpField = document.querySelector("#nomortelepon");
        let nomorHPField = document.querySelector("#nomorhandphone");

        identitasField.textContent = detail.nomorIdentitas;
        namaField.textContent = detail.nama;
        kelaminField.textContent = detail.jenisKelamin;
        npwpField.textContent = detail.npwp;
        alamatIdenField.textContent = detail.alamatIdentitas;
        kelIdenField.textContent = detail.kelurahanIdentitas;
        kodPosIdenField.textContent = detail.kodePosIdentitas;
        kecIdenField.textContent = detail.kecamatanIdentitas;
        kabIdenField.textContent = detail.kotaIdentitas;
        provIdenField.textContent = detail.provinsiIdentitas;
        if (detail.kelurahanIdentitas == detail.kelurahanDomisili){
            alamatSamaField.checked = true;
        }
        alamatDomField.textContent = detail.alamatDomisili;
        kelDomField.textContent = detail.kelurahanDomisili;
        kodPosDomField.textContent = detail.kodePosDomisili;
        kecDomField.textContent = detail.kecamatanDomisili;
        kabDomField.textContent = detail.kotaDomisili;
        provDomField.textContent = detail.provinsiDomisili;
        tempatLahirField.textContent = detail.tempatLahir;
        tanggalLahirField.textContent = detail.tanggalLahir;
        nomorTelpField.textContent = detail.nomorTelepon;
        nomorHPField.textContent = detail.nomorHandphone;
    }

    let populateDetail2 = (detail) => {
        let bankField = document.querySelector("#bank");
        let rekField = document.querySelector("#rek");
        let namaRekField = document.querySelector("#namarekening");

        bankField.textContent = detail.bank;
        rekField.textContent = detail.nomorRekening;
        namaRekField.textContent = detail.namaRekening;
    }
})()