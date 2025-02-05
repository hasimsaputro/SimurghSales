(()=>{

    document.addEventListener('DOMContentLoaded', function(){
        let mainUrl = "http://localhost:8082/api";

        let bodiTabelProduk = document.querySelector(".produk-list tbody");
        let idCabang = document.querySelector("#id").value;
        let checkedProduk = [];

        fetch(`${mainUrl}/cabang/produk/${idCabang}`)
        .then(response => response.json())
        .then(data => {
            checkedProduk = data.map(option => option.id);
        })


        setTimeout(() => {
            fetch(`${mainUrl}/produk`).then(response => response.json()).then(data => {
                for (const produk of data) {
                    let row = document.createElement("tr");
                    let columnCheckbox = document.createElement("td");
                    let columnKode = document.createElement("td");
                    let columnName = document.createElement("td");
                    let inputCheckbox = document.createElement("input");
                    inputCheckbox.setAttribute("type", "checkbox");
                    inputCheckbox.name = "produk";
                    inputCheckbox.id = produk.kodeProduk;
    
                    // Mengecek apakah produk ada di dalam array produkChecked
                    if (checkedProduk.includes(produk.kodeProduk)) {
                        inputCheckbox.checked = true; // Menandai checkbox sebagai tercentang jika ID ada di produkChecked
                    }
    
                    columnKode.innerHTML = produk.kodeProduk;
                    columnName.innerHTML = produk.namaProduk;
                    columnCheckbox.appendChild(inputCheckbox);
                    row.appendChild(columnCheckbox);
                    row.appendChild(columnKode);
                    row.appendChild(columnName);
                    bodiTabelProduk.appendChild(row);
                }
            })
        }, 1000)




        let saveButton = document.querySelector(".submit-button");
        saveButton.addEventListener("click", () => {
            // Get form values
            let idCabang = document.querySelector("#id").value;
            let namaCabang = document.querySelector("#namaCabang").value;
            let tipeStruktur = document.querySelector("#tipeStruktur").value;
            let alamat = document.querySelector("#address").value;
            let kodePos = document.querySelector("#kodePos").value;
            let kelurahan = document.querySelector("#kelurahan").value;
            let kecamatan = document.querySelector("#kecamatan").value;
            let kotaKabupaten = document.querySelector("#kotakabupaten").value;
            let provinsi = document.querySelector("#provinsi").value;
            let nomorTelepon = document.querySelector("#nomortelepon").value;
            let nomorNpwp = document.querySelector("#NPWP").value;
            // Assuming you want to get the date value as well
            let tanggalBerdiri = document.querySelector("#tanggalBerdiri").value;  // Assuming an input with id 'tanggalBerdiri'

            // Get the status (checkbox or select value)
            let status = document.querySelector('input[name="status"]:checked').value;

            // Get the selected product IDs (checkboxes)
            let produkList = document.querySelectorAll("input[type='checkbox'][name='produk']");
            let ids = Array.from(produkList).filter(checkbox => checkbox.checked).map(checkbox => parseInt(checkbox.id)); // Convert to integer IDs

            // Create the request body object
            let requestBody = {
                id: parseInt(idCabang),  // Convert to integer if needed
                namaCabang: namaCabang,
                tipeStruktur: tipeStruktur,
                alamat: alamat,
                kodePos: kodePos,
                kelurahan: kelurahan,
                kecamatan: kecamatan,
                kotaKabupaten: kotaKabupaten,
                provinsi: provinsi,
                nomorTelepon: nomorTelepon,
                npwp: nomorNpwp,
                tanggalBerdiri: tanggalBerdiri,  // Ensure this is formatted correctly (e.g., 'YYYY-MM-DD')
                produkList: ids,
                status: status == 1 ? true : false
            };

            // Send the request using Fetch API
            fetch(`${mainUrl}/cabang`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
            })
            .then(response => response.json())
            .then(data => {
                window.location.href = "http://localhost:8082/cabang";
            })
            .catch(error => {
                console.error('Error:', error);
            });
            
        })

    })
     

})()